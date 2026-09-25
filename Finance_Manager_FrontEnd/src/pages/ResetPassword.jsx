import {useState} from "react";
import {useSearchParams, useNavigate} from "react-router-dom";
import toast from "react-hot-toast";
import axios from "axios";
import {BASE_URL} from "../util/apiEndpoints.js";

const ResetPassword = () => {

    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    const token = searchParams.get("token");

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const handleResetPassword = async (e) => {
        e.preventDefault();

        if (!token) {
            toast.error("Invalid password reset link.");
            return;
        }

        if (!newPassword || !confirmPassword) {
            toast.error("Please fill in all fields.");
            return;
        }

        if (newPassword !== confirmPassword) {
            toast.error("Passwords do not match.");
            return;
        }

        if (newPassword.length < 6) {
            toast.error("Password must be at least 6 characters.");
            return;
        }

        try {
            setLoading(true);

            const response = await axios.post(
                `${BASE_URL}/reset-password?token=${encodeURIComponent(token)}&newPassword=${encodeURIComponent(newPassword)}`
            );

            toast.success(response.data);

            setTimeout(() => {
                navigate("/login");
            }, 1500);

        } catch (error) {
            toast.error(
                error.response?.data || "Unable to reset password."
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
                        Reset Password
                    </h1>

                    <p className="text-gray-500 mt-2">
                        Enter your new password below
                    </p>
                </div>

                <form onSubmit={handleResetPassword} className="space-y-5">

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">
                            New Password
                        </label>

                        <input
                            type="password"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            placeholder="Enter new password"
                            className="w-full px-4 py-3 border border-gray-300 rounded-lg outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">
                            Confirm Password
                        </label>

                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="Confirm new password"
                            className="w-full px-4 py-3 border border-gray-300 rounded-lg outline-none focus:ring-2 focus:ring-green-500 focus:border-green-500"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50"
                    >
                        {loading ? "Resetting..." : "Reset Password"}
                    </button>

                </form>

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

export default ResetPassword;
