import {useContext,useState} from "react";
import {Link,useNavigate} from "react-router-dom";
import {assets} from "../assets/assets.js";

import axiosConfig from "../util/axiosConfig.jsx";
import {API_ENDPOINTS} from "../util/apiEndpoints.js";
import {AppContext} from "../context/AppContext.jsx";
import {LoaderCircle} from "lucide-react";
import Input from "../components/Input.jsx";

const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    const [isLoading, setIsLoading] = useState(false);
    const {setUser} = useContext(AppContext);

    const navigate = useNavigate();

    const validateEmail = (email) => {
        if (email.trim()) {
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return regex.test(email);
        }
        return false;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        //basic validation
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

        setError("");

        //Login api
        try {
            const response = await axiosConfig.post(API_ENDPOINTS.LOGIN, {
                email,
                password,
            });

            const {token, user} = response.data

            if (token) {
                localStorage.setItem("token", token);
                setUser(user);
                navigate("/dashboard");
            }
        }catch(error) {
            if (error.response && error.response.data.message) {
                setError(error.response.data.message);
            } else {
                console.error('Something went wrong', error);
                setError(error.message);
            }
        } finally {
            setIsLoading(false);
        }

    }

    return(


        <div className="h-screen w-full flex flex-col font-sans">
            <div className="flex-grow w-full relative flex items-center justify-center overflow-hidden">
                {/*  image  blur */}
                <img src={assets.loginBackground} alt="Background" className="absolute inset-0 w-full h-full object-cover filter blur-sm" />

                <div className="relative z-10 w-full max-w-[95%] sm:max-w-md px-6">
                    <div className="bg-white bg-opacity-95 backdrop-blur-sm rounded-2xl shadow-2xl p-8 border border-emerald-100">
                        <h3 className="text-2xl font-bold text-emerald-900 text-center mb-2">
                            Welcome Back
                        </h3>
                        <p className="text-sm text-slate-600 text-center mb-8">
                            Please enter your email and password to login
                        </p>

                        <form onSubmit={handleSubmit} className="space-y-4">
                            <Input
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                label="Email Address"
                                placeholder="name@example.com"
                                type="text"
                            />

                            <Input
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                label="Password"
                                placeholder="*********"
                                type="password"
                            />


                            {error && (
                                <div className="flex items-center justify-center bg-red-50 border-l-4 border-red-600 p-3 rounded shadow-sm">
                                    <p className="text-red-800 text-sm font-bold">
                                        {error}
                                    </p>
                                </div>
                            )}

                            <button
                                disabled={isLoading}
                                className={`w-full py-3.5 rounded-xl text-white text-lg font-semibold flex items-center justify-center gap-2 transition-all
                        ${isLoading ? 'bg-emerald-300 cursor-not-allowed' : 'bg-emerald-600 hover:bg-emerald-700 active:scale-95 shadow-lg shadow-emerald-200'}`}
                                type="submit"
                            >
                                {isLoading ? (
                                    <>
                                        <LoaderCircle className="animate-spin w-5 h-5" />
                                        Logging in...
                                    </>
                                ) : (
                                    "LOGIN"
                                )}
                            </button>

                            <p className="text-sm text-slate-700 text-center mt-6">
                                Don't have a finance manager account?{" "}
                                <Link to="/signup" className="font-bold text-emerald-600 underline hover:text-emerald-800 transition-colors">
                                    Signup
                                </Link>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    )

}

export default Login;