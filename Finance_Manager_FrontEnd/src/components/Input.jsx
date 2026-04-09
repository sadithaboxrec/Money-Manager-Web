import React, { useState } from "react";
import { Eye, EyeOff } from "lucide-react";

const Input = ({ label, value, onChange, placeholder, type, isSelect, options }) => {
    const [showPassword, setShowPassword] = useState(false);

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="mb-4">
            <label className="text-[13px] font-semibold text-slate-700 block mb-1.5 ml-1">
                {label}
            </label>

            <div className="relative">
                {isSelect ? (
                    <select
                        className="w-full bg-white border border-slate-200 rounded-xl py-3 px-4 text-[16px] md:text-sm transition-all focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 outline-none appearance-none"
                        value={value}
                        onChange={(e) => onChange(e.target.value)}
                    >
                        {options.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                ) : (
                    <input
                        className="w-full bg-white border border-slate-200 rounded-xl py-3 px-4 text-[16px] md:text-sm transition-all focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 outline-none placeholder:text-slate-400"
                        type={type === 'password' ? (showPassword ? 'text' : 'password') : type}
                        placeholder={placeholder}
                        value={value}
                        onChange={(e) => onChange(e)}
                    />
                )}

                {type === 'password' && (
                    <button
                        type="button"
                        onClick={toggleShowPassword}
                        className="absolute right-3 top-1/2 -translate-y-1/2 p-1 focus:outline-none"
                    >
                        {showPassword ? (
                            <Eye size={20} className="text-emerald-600" />
                        ) : (
                            <EyeOff size={20} className="text-slate-400" />
                        )}
                    </button>
                )}
            </div>
        </div>
    );
};

export default Input;