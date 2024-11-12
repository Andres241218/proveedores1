import { Category } from "./Category";
import { Evaluation } from "./Evaluation";
import { Predecessor } from "./Predecesor";
import { Question } from "./Question";

export interface Supplier {
    suppliersId: number;      
    suppliersNit: string;
    suppliersName: string;    
    suppliersPhone?: string; 
    suppliersAddress?: string; 
    suppliersType?: string;  
    predecessor?: Predecessor;
    suppliersState?: string;   
    categoryName?: Category;  
    evaluations?: Evaluation[] ; 
    questions?: Question[] ; 
}