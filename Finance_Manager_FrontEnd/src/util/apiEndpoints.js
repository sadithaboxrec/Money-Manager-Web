export const BASE_URL = import.meta.env.VITE_API_URL;
const CLOUDINARY_NAME = "dbewbgkdb";


export const API_ENDPOINTS = {
    LOGIN: "/login",
    REGISTER: "/register",
    UPLOAD_IMAGE: `https://api.cloudinary.com/v1_1/${CLOUDINARY_NAME}/image/upload`,
    GET_USER_INFO: "/profile",   // to make sure that after reloading , user data still to didplay

    // categories
     GET_ALL_CATEGORIES: "/categories",
     ADD_CATEGORY:"/categories",
     UPDATE_CATEGORY:(categoryId)=>`/categories/${categoryId}`,

    //  income
    GET_ALL_INCOMES: "/incomes",
    CATEGORY_BY_TYPE: (type) => `/categories/${type}`,
    ADD_INCOME: "/incomes",
    DELETE_INCOME: (incomeId) => `/incomes/${incomeId}`,

    INCOME_EXCEL_DOWNLOAD: "excel/download/income",
    EMAIL_INCOME: "/email/income-excel",

    // expenses

    GET_ALL_EXPENSE: "/expenses",
    ADD_EXPENSE: "/expenses",
    DELETE_EXPENSE: (expenseId) => `/expenses/${expenseId}`,
    EXPENSE_EXCEL_DOWNLOAD: "excel/download/expense",
    EMAIL_EXPENSE: "/email/expense-excel",

    // fltetrs
    APPLY_FILTERS: "/filter",
    DASHBOARD_DATA: "/dashboard",
}

