export const BASE_URL = "http://localhost:8080/api/v1.0";
const CLOUDINARY_NAME = "dbewbgkdb";


export const API_ENDPOINTS = {
    LOGIN: "/login",
    REGISTER: "/register",
    UPLOAD_IMAGE: `https://api.cloudinary.com/v1_1/${CLOUDINARY_NAME}/image/upload`,

}