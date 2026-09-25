import {useRef, useState} from "react";

import { User, Upload, Trash } from "lucide-react";


const PfUploader =({image,setImage}) =>{

    // useRef  used to access the hidden file input directly.T0 custom upload button to trigger the file picker.
    const inputRef = useRef(null);
    //  temporary URL used to display the selected image preview.
    const[previewUrl,setPreviewUrl] =useState(null);


    const handleImageChange = (e) => {

        // Get the first image
        const file = e.target.files[0];

        //  if a file  actually there.
        if (file) {


            setImage(file);

            //  temporary browser URL for displaying the image before it is uploaded to the backend.
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

        // Programmatically click the hidden file input. The user sees the normal file-selection dialog.
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

                    {/* profile icon */}
                    <User className="text-green-600" size={55} />

                    <button
                        onClick={onChooseFile}
                        className="w-8 h-8 flex items-center justify-center bg-primary
                        text-white rounded-full absolute -bottom-1 -right-1">

                        <Upload size={25} className="text-green-600" />
                    </button>

                </div>

            ): (

                // Displayed after the selected an image.
                <div className="relative">

                    {/* 
                        Shows the selected image using the temporary preview URL.
                        This lets the user see the image before uploading it.
                    */}
                    <img src={previewUrl} alt="profile photo" className="w-20 h-20 rounded-full object-cover" />

                    <button
                        onClick={handleRemoveImage}
                        className="w-8 h-8 flex items-center justify-center bg-red-800 text-white rounded-full absolute -bottom-1 -right-1">

                        {/* Button used to remove the selected image */}
                        <Trash size={15}/>

                    </button>

                </div>
            )}

        </div>
    )


}

export default PfUploader;





// User clicks Upload
//        ↓
// onChooseFile()
//        ↓
// hidden <input type="file"> is clicked
//        ↓
// User selects image
//        ↓
// handleImageChange()
//        ↓
// setImage(file)          → sends File to parent
// setPreviewUrl(preview)  → displays image immediately
//        ↓
// User sees preview
//        ↓
// User clicks Trash
//        ↓
// handleRemoveImage()
//        ↓
// setImage(null)
// setPreviewUrl(null)
