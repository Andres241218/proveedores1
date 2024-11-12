import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EvaluationSave } from '../models/EvaluationSave';
import { GenericResponse } from '../models/generic-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  private apiUrl = 'http://localhost:8085/api/evaluations'; 

  constructor(private http: HttpClient) { }

  addQuestion(evaluation:EvaluationSave): Observable<GenericResponse> {
    return this.http.post<GenericResponse>(this.apiUrl, evaluation);
  }

  
}
