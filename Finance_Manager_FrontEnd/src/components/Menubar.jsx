import { useState, useRef, useEffect, useContext } from "react";
import { User, LogOut, X, Menu } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { assets } from "../assets/assets.js";
import { AppContext } from "../context/AppContext.jsx";
import Sidebar from "./Sidebar.jsx";

const Menubar = ({ activeMenu }) => {
    const [openSideMenu, setOpenSideMenu] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);

    // Reference to the dropdown so we can detect clicks outside it.
    const dropdownRef = useRef(null);

    // Get user information and logout function from global context.
    const { clearUser, user } = useContext(AppContext);

    const navigate = useNavigate();

    // Close dropdown when the user clicks outside of it.
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                dropdownRef.current &&
                !dropdownRef.current.contains(event.target)
            ) {
                setShowDropdown(false);
            }
        };

        // Only listen for outside clicks while dropdown is open.
        if (showDropdown) {
            document.addEventListener("mousedown", handleClickOutside);
        }

        // Remove event listener when component updates/unmounts.
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [showDropdown]);

    // Toggle the profile dropdown.
    const toggleDropdown = () => {
        setShowDropdown(!showDropdown);
    };

    // Clear login data and redirect to login page.
    const handleLogout = () => {
        localStorage.clear();
        clearUser();
        setShowDropdown(false);
        navigate("/login");
    };


    
    return (
        <div className="flex items-center justify-between gap-5 bg-white
                        border border-b border-gray-200/50
                        backdrop-blur-[2px] py-4 px-4 sm:px-7
                        sticky top-0 z-30">

            {/* Left side - Mobile menu button and application title */}
            <div className="flex items-center gap-5">

                {/* Mobile sidebar toggle */}
                <button
                    className="block lg:hidden text-green-700 hover:bg-green-50
                               p-1 rounded transition-colors"
                    onClick={() => {
                        setOpenSideMenu(!openSideMenu);
                    }}
                >
                    {openSideMenu ? (
                        <X className="text-2xl text-green-700" />
                    ) : (
                        <Menu className="text-2xl text-green-700" />
                    )}
                </button>

                {/* Logo and application name */}
                <div className="flex items-center gap-2">
                    <img
                        src={assets.logo}
                        alt="logo"
                        className="h-10 w-10"
                    />

                    <span className="text-lg font-medium text-green-800 truncate">
                        Money Manager
                    </span>
                </div>
            </div>

            {/* Right side - Profile avatar and dropdown */}
            <div className="relative" ref={dropdownRef}>

                {/* Profile button */}
                <button
                    onClick={toggleDropdown}
                    className="flex items-center justify-center
                               w-10 h-10
                               bg-green-100 hover:bg-green-200
                               rounded-full
                               transition-colors duration-200
                               focus:outline-none
                               focus:ring-2 focus:ring-green-600
                               focus:ring-offset-2"
                >
                    {user?.profileImageUrl ? (
                        <img
                            src={user.profileImageUrl}
                            alt="profile"
                            className="w-10 h-10 rounded-full object-cover"
                        />
                    ) : (
                        <User className="w-5 h-5 text-green-600" />
                    )}
                </button>

                {/* Profile Dropdown */}
                {showDropdown && (
                    <div className="absolute right-0 mt-2 w-56
                                    bg-white rounded-lg
                                    shadow-lg
                                    border border-green-100
                                    py-1 z-50">

                        {/* User information */}
                        <div className="px-4 py-3 border-b border-green-100">

                            <div className="flex items-center gap-3">

                                {/* Small profile image */}
                                <div className="flex items-center justify-center
                                                w-9 h-9
                                                bg-green-100
                                                rounded-full overflow-hidden">

                                    {user?.profileImageUrl ? (
                                        <img
                                            src={user.profileImageUrl}
                                            alt="profile"
                                            className="w-9 h-9 rounded-full object-cover"
                                        />
                                    ) : (
                                        <User className="w-4 h-4 text-green-600" />
                                    )}
                                </div>

                                {/* User name and email */}
                                <div className="flex-1 min-w-0">
                                    <p className="text-sm font-medium text-gray-900 truncate">
                                        {user?.fullName}
                                    </p>

                                    <p className="text-xs text-gray-500 truncate">
                                        {user?.email}
                                    </p>
                                </div>
                            </div>
                        </div>

                        {/* Dropdown options */}
                        <div className="py-1">

                            {/* Logout button */}
                            <button
                                onClick={handleLogout}
                                className="flex items-center gap-3
                                           w-full px-4 py-2
                                           text-sm text-gray-700
                                           hover:bg-green-50
                                           hover:text-green-700
                                           transition-colors duration-150"
                            >
                                <LogOut className="w-4 h-4 text-green-600" />
                                <span>Logout</span>
                            </button>

                        </div>
                    </div>
                )}
            </div>

            {/* Mobile side menu */}
            {openSideMenu && (
                <div className="fixed top-[73px] left-0 right-0
                                bg-white
                                border-b border-green-100
                                lg:hidden z-20">

                    <Sidebar activeMenu={activeMenu} />

                </div>
            )}
        </div>
    );
};

export default Menubar;
