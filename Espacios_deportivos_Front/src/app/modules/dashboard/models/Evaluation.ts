import { Category } from "./Category";
import { Question } from "./Question";

export interface Evaluation {
    id?: number;
    date?: Date;
    rating?: number;
    detail?:string;
    category?: Category | null; 
    questionId?: Question | null; 
    


}