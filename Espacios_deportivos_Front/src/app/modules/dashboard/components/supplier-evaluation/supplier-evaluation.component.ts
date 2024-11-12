import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { QuestionService } from '../../services/question.service';
import { Question } from '../../models/Question';
import { GenericResponse } from '../../models/generic-response';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { SuppliersService } from '../../services/suppliers.service';
import { Supplier } from '../../models/Supplier';
import { EvaluationService } from '../../services/evaluation.service';
import { EvaluationSave } from '../../models/EvaluationSave';
import { Router, RouterModule } from '@angular/router';
import {
  MatSlideToggleModule,
} from '@angular/material/slide-toggle';

@Component({
  selector: 'app-supplier-evaluation',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatSelectModule,
    RouterModule, MatSlideToggleModule,
  ],
  templateUrl: './supplier-evaluation.component.html',
  styleUrls: ['./supplier-evaluation.component.scss'],
})
export class SupplierEvaluationComponent implements OnInit {
  providerID: number = 0;
  providerName: string = '';
  providerNit: string = '';
  providerType: string = '';
  providerCategory: string = '';
  providerPredecessor: string = '';
  selectedOption: string = '';
  errorMessage: string = '';
  message: string = '';
  supplier!: Supplier;
  isLiveBoxVisible: boolean = false;

  options: string[] = [
    'Garantía extendida',
    'Soporte técnico',
    'Variedad de equipos (laptops, desktops)',
    'Personalización de configuraciones',
    'Sustancias químicas',
    'Insumos',
    'Combustibles y lubricantes',
  ];

  evaluationCriteria: string = 'Criterios de Evaluación';

  criterios: string[] = [
    'Servicio Preventa',
    'Cumplimiento en los Tiempos de Entrega',
    'Calidad del Producto',
    'Logística',
    'Servicio Postventa',
    'Sistema de Gestión de Seguridad y Salud en el Trabajo',
  ];

  questions: (Question & { rating: number; applies: boolean })[] = [];
  stars: number[] = [1, 2, 3, 4, 5];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private suppliersService: SuppliersService,
    private evaluationService: EvaluationService,
    private questionService: QuestionService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.providerID = params['id'] || '';
      this.providerName = params['name'] || '';
      this.providerNit = params['nit'] || '';
      this.providerType = params['type'] || '';
      this.providerCategory = params['category'] || '';
      this.providerPredecessor = params['predecessor'] || '';
    });
    this.initializeQuestions();
  }

  get responseCount(): number {
    return this.questions.filter((q) => q.rating > 0).length;
  }

  get totalQuestions(): number {
    return this.questions.length;
  }

  getQuestionsByCriterio(criterio: string): Question[] {
    return this.questions.filter((q) => q.feature === criterio);
  }

  private initializeQuestions(): void {
    this.questionService.getAllQuestion().subscribe(
      (data: GenericResponse) => {
        this.questions = data.data;
        this.message=data.message;
        console.log(this.questions);
      },
      (error) => {
        console.error('Error fetching suppliers:', error);
      }
    );
  }

  rate(question: Question & { rating: number }, rating: number): void {
    if (question.applies !== undefined && question.applies) {
      question.rating = rating;
    }
  }

  isStarActive(question: Question & { rating: number }, star: number): boolean {
    return question.applies !== undefined && question.applies && question.rating >= star;
  }

  saveEvaluation(): void {
    if (this.validateEvaluation()) {
      const averageRating = this.calculateTotalAverageRating();

      let evaluationDTO: EvaluationSave;

      evaluationDTO = {
        date: new Date(),
        rating: averageRating,
        detail: this.selectedOption,
        usuarioId: 1,
        proveedorId: this.providerID,
      };
      console.log('Saving evaluation...', evaluationDTO);
      this.evaluationService.addQuestion(evaluationDTO).subscribe(
        (response) => {
          console.log('Evaluation saved:', response);
          this.errorMessage = 'GOOD JOB!!';
          this.router.navigate(['/supplier-manager']);
        },
        (error) => {
          console.error('Error saving evaluation:', error);
          this.errorMessage = 'An error occurred while saving the evaluation';
        }
      );
    } else {
      this.errorMessage = 'Please complete all required fields';
    }
  }

  private validateEvaluation(): boolean {
    return this.questions.every((question) => question.rating > 0 || !question.applies);
  }

  calculateAverageRatingForCriterio(criterio: string): number {
    const questionsForCriterio = this.questions.filter(question => question.feature === criterio && question.applies);

    if (questionsForCriterio.length === 0) {
      return 0; 
    }

    const totalRating = questionsForCriterio.reduce(
      (sum, question) => sum + question.rating,
      0
    );
    return totalRating / questionsForCriterio.length;
  }

  calculateTotalAverageRating(): number {
    const applicableQuestions = this.questions.filter(q => q.applies);
    const totalRating = applicableQuestions.reduce((sum, question) => sum + question.rating, 0);
    const totalQuestionsWithRating = applicableQuestions.filter(q => q.rating > 0).length;
    return totalQuestionsWithRating ? totalRating / totalQuestionsWithRating : 0;
  }

  toggleLiveBox(): void {
    this.isLiveBoxVisible = !this.isLiveBoxVisible;
  }

  trackByQuestionId(index: number, question: Question): number {
    return question.questionId ?? index;  // Unique identifier for each question, fallback to index
  }
}