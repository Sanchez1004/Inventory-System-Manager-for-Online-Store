import { Address } from "./address";

export type Client  = {
    id: number;
    firstName?: string;
    lastName?: string;
    email: string;
    phone?: string;
    password: string;
    role: string;
    address?: Address;
};
