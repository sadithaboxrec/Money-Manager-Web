import {useRef, useState} from "react";

import { User, Upload, Trash } from "lucide-react";


const PfUploader =({image,setImage}) =>{

    const inputRef = useRef(null);
    const[previewUrl,setPreviewUrl] =useState(null);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);

            const preview = URL.createObjectURL(file);
            setPreviewUrl(preview);
        }
    }

    const handleRemoveImage = (e) => {
        e.preventDefault();
        setImage(null);
        setPreviewUrl(null);
    }

    const onChooseFile = (e) => {
        e.preventDefault();
        inputRef.current?.click();
    }

    return (

        <div className="flex justify-center mb-6">
            <input type="file"
                   accept="image/*"
                   ref={inputRef}
                   onChange={handleImageChange}
                   className="hidden"
            />

            {!image ? (
                <div className="w-20 h-20 flex items-center justify-center bg-green-200 rounded-full relative">
                    <User className="text-green-600" size={55} />

                    <button
                        onClick={onChooseFile}
                        className="w-8 h-8 flex items-center justify-center bg-primary
                        text-white rounded-full absolute -bottom-1 -right-1">

                        <Upload size={25} className="text-green-600" />
                    </button>
                </div>
            ): (
                <div className="relative">
                    <img src={previewUrl} alt="profile photo" className="w-20 h-20 rounded-full object-cover" />
                    <button
                        onClick={handleRemoveImage}
                        className="w-8 h-8 flex items-center justify-center bg-red-800 text-white rounded-full absolute -bottom-1 -right-1">
                        <Trash size={15}/>
                    </button>
                </div>
            )}

        </div>

    )


}

export default PfUploader;