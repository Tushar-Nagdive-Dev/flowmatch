export interface Invoice {
  invoiceId: string;
  customerName: string;
  amount: number;
  status: string;
  issueDate: string;
}

export interface ReconciliationRecord {
  reconciliationId: string;
  invoiceId: string;
  status: string;
  matchedAmount: number;
  reconciliationDate: string;
}

export interface UserRecord {
  username: string;
  email: string;
  role: string;
  status: string;
}