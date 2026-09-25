import {useState} from "react";
import {LoaderCircle} from "lucide-react";


// handle deletions loader
const DeleteAlert = ({content, onDelete}) => {
    const [loading, setLoading] = useState(false);
    const handleDelete = async () => {
        setLoading(true);
        try {
            await onDelete();
        }finally {
            setLoading(false);
        }
    }
    return (
        <div className="rounded-xl border-2 border-rose-200 bg-rose-50 p-4 text-rose-950">
            <p className="text-sm">{content}</p>
            <div className="flex justify-end mt-6">
                <button
                    onClick={handleDelete}
                    disabled={loading}
                    type="button"
                    className="add-btn add-btn-fill border-2 border-rose-700 bg-rose-600 text-white hover:bg-rose-700 focus-visible:ring-2 focus-visible:ring-rose-500 focus-visible:ring-offset-2">
                    {loading ? (
                        <>
                            <LoaderCircle className="h-4 w-4 animated-spin" />
                            Deleting...
                        </>
                    ): (
                        <>
                            Delete
                        </>
                    )}
                </button>
            </div>
        </div>
    )
}

export default DeleteAlert;
