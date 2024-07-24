export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    birthdate: string;
    gender: string;
    address?: string;
    phoneNumber?: string;
}