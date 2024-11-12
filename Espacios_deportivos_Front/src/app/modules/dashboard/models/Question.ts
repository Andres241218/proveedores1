
export interface Question {
    questionId?: number;
    question?: string;
    feature?: string;
    rating: number | 0;
    applies?: boolean;
}