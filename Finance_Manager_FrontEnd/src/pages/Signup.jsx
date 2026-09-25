import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {assets} from "../assets/assets.js";
import Input from "../components/Input.jsx";

import axiosConfig from "../util/axiosConfig.jsx";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";
import toast from "react-hot-toast";
import {LoaderCircle} from "lucide-react";
import PfUploader from "../components/PfUploader.jsx";
import uploadProfileImage from "../util/uploadProfileImage.js";


const Signup = () => {

    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    const [isLoading, setIsLoading] = useState(false);

    const [profilePhoto,setProfilePhoto] = useState(null);


    const navigate = useNavigate();

    const validateEmail = (email) => {
        if (email.trim()) {
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return regex.test(email);
        }
        return false;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();  // stop reloading entire web

        let profileImageUrl = "";

        setIsLoading(true);

        //basic validation for inputs

        if (!fullName.trim()) {
            setError("Please enter your fullname");
            setIsLoading(false);
            return;
        }

        if (!validateEmail(email)) {
            setError("Please enter valid email address");
            setIsLoading(false);
            return;
        }

        if (!password.trim()) {
            setError("Please enter your password");
            setIsLoading(false);
            return;
        }

        console.log(fullName,email,password);

        setError("");  // to set error to empty

        // api calling
        try {

            // upload the image if there
            if(profilePhoto){

                const imageUrl=await uploadProfileImage(profilePhoto);

                profileImageUrl= imageUrl || "";


            }

            const response = await axiosConfig.post(API_ENDPOINTS.REGISTER, {
                fullName,
                email,
                password,
                profileImageUrl
            })
            if (response.status === 201) {
                toast.success("Finanacee Manager created successfully.");
                navigate("/login");
            }
        } catch(err) {
            console.error('Something went wrong', err);
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    }

    return(

        <div className="min-h-screen w-full flex flex-col">

            <div className="flex-grow w-full relative flex items-center justify-center py-10 md:py-20 overflow-y-auto">
                {/* Background image */}
                <img src={assets.loginBackground} alt="Background" className="absolute inset-0 w-full h-full object-cover filter blur-sm" />

                <div className="relative z-10 w-full max-w-[95%] sm:max-w-md md:max-w-lg px-4">
                    <div className="bg-white bg-opacity-95 backdrop-blur-md rounded-2xl shadow-2xl p-6 md:p-10 border border-emerald-100">

                        <header className="mb-8">
                            <h3 className="text-2xl md:text-3xl font-bold text-emerald-950 text-center">
                                Create Account
                            </h3>
                            <p className="text-sm text-slate-500 text-center mt-2">
                                Join us and start managing your finances smarter.
                            </p>
                        </header>

                        <form onSubmit={handleSubmit} className="space-y-5">
                            {/* Grid: 1 col on mobile, 2 cols on tablet+ */}

                            <div className="flex justify-center mb-6">
                                <PfUploader image={profilePhoto} setImage={setProfilePhoto}/>
                            </div>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-4 gap-y-1">
                                <Input
                                    value={fullName}
                                    onChange={(e) => setFullName(e.target.value)}
                                    label="Full Name"
                                    placeholder="Your Name"
                                    type="text"
                                />

                                <Input
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    label="Email Address"
                                    placeholder="you@example.com"
                                    type="text"
                                />

                                <div className="md:col-span-2">
                                    <Input
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        label="Password"
                                        placeholder="*********"
                                        type="password"
                                    />
                                </div>
                            </div>

                            {/*  Error Message */}
                            {error && (
                                <div className="flex items-center gap-2 bg-red-50 border-l-4 border-red-600 p-4 rounded shadow-sm animate-shake">
                                    <div className="bg-red-600 rounded-full p-1">
                                        <svg className="w-3 h-3 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="3" d="M6 18L18 6M6 6l12 12" />
                                        </svg>
                                    </div>
                                    <p className="text-red-700 text-sm font-bold">
                                        {error}
                                    </p>
                                </div>
                            )}

                            <button
                                disabled={isLoading}
                                className={`w-full py-4 rounded-xl text-white font-bold tracking-wide transition-all shadow-lg
                        ${isLoading ? 'bg-emerald-300' : 'bg-emerald-600 hover:bg-emerald-700 hover:shadow-emerald-200 active:scale-95'}`}
                                type="submit"
                            >
                                {isLoading ? "CREATING ACCOUNT..." : "SIGN UP"}
                            </button>

                            <p className="text-sm text-slate-600 text-center pt-4">
                                Already have an account?{" "}
                                <Link to="/login" className="font-bold text-emerald-600 hover:text-emerald-800 transition-colors">
                                    Login
                                </Link>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    )


}

export default Signup;