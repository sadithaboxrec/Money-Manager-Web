import {API_ENDPOINTS} from "./apiEndpoints.js";

const CLOUDINARY_UPLOAD = "fiancil_manager";

const uploadProfileImage = async (image) => {

    const formData = new FormData();
    formData.append("file", image);
    formData.append("upload_preset", CLOUDINARY_UPLOAD);

    try {
        const response = await fetch(API_ENDPOINTS.UPLOAD_IMAGE, {
            method: "POST",
            body: formData
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Upload to Cloudinary  failed: ${errorData.error.message || response.statusText}`);
        }

        const data = await response.json();
        console.log('Image uploaded successfully.', data);
        return data.secure_url;
    } catch (error) {
        console.error("Error uploading the image", error);
        throw error;
    }

}

export default uploadProfileImage;