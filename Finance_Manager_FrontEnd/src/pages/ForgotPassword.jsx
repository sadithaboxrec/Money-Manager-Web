import {useState} from "react";
import {useNavigate} from "react-router-dom";
import toast from "react-hot-toast";
import axios from "axios";
import {BASE_URL} from "../util/apiEndpoints.js";

const ForgotPassword = () => {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);
    const [sent, setSent] = useState(false);

    const handleForgotPassword = async (e) => {
        e.preventDefault();

        if (!email) {
            toast.error("Please enter your email.");
            return;
        }

        try {
            setLoading(true);

            const response = await axios.post(
                `${BASE_URL}/forgot-password?email=${encodeURIComponent(email)}`
            );

            toast.success(response.data);
            setSent(true);

        } catch (error) {
            toast.error(
                error.response?.data || "Unable to send reset link."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-green-50 flex items-center justify-center px-4">

            <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8">

                <div className="text-center mb-8">
                    <h1 className="text-3xl font-bold text-green-700">
                        Forgot Password?
                    </h1>

                    <p className="text-gray-500 mt-2">
                        Enter your email to receive a password reset link.
                    </p>
                </div>

                {!sent ? (
                    <form onSubmit={handleForgotPassword} className="space-y-5">

                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-2">
                                Email
                            </label>

                            <input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="Enter your email"
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500"
                            />
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50"
                        >
                            {loading ? "Sending..." : "Send Reset Link"}
                        </button>

                    </form>
                ) : (
                    <div className="text-center">

                        <div className="bg-green-50 border border-green-200 rounded-lg p-4">
                            <p className="text-green-700">
                                Password reset link has been sent to your email.
                            </p>
                        </div>

                        <p className="text-gray-500 text-sm mt-4">
                            Please check your inbox and click the link to reset your password.
                        </p>

                    </div>
                )}

                <div className="text-center mt-6">
                    <button
                        onClick={() => navigate("/login")}
                        className="text-green-600 hover:text-green-700 font-medium"
                    >
                        Back to Login
                    </button>
                </div>

            </div>

        </div>
    );
};

export default ForgotPassword;
