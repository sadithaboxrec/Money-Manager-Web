import { useContext, useEffect } from "react";
import { AppContext } from "../context/AppContext.jsx";
import { useNavigate } from "react-router-dom";
import axiosConfig from "../util/axiosConfig.jsx";
import { API_ENDPOINTS } from "../util/apiEndpoints.js";

export const useUser = () => {
    const { user, setUser, clearUser } = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(() => {
        // If the user already exists in the AppContext,
        // there is no need to make another API request.
        if (user) {
            return;
        }

        // Used to prevent updating React state if the component
        // gets unmounted before the API request finishes.
        let isMounted = true;

        const fetchUserInfo = async () => {
            try {
                // Fetch the currently authenticated user's information
                // from the backend.
                //
                // This is especially important after a page reload because
                // React Context state is reset when the application reloads.
                const response = await axiosConfig.get(
                    API_ENDPOINTS.GET_USER_INFO
                );

                // Only update the context if the component is still mounted
                // and the backend returned user information.
                if (isMounted && response.data) {
                    setUser(response.data);
                }

            } catch (error) {
                console.log("Failed to fetch the user info", error);

                if (isMounted) {
                    // If fetching the user fails, clear any existing user data
                    // and redirect the user to the login page.
                    clearUser();
                    navigate("/login");
                }
            }
        };

        fetchUserInfo();

        // Cleanup function runs when the component using this hook unmounts.
        // It prevents the API response from updating state after unmounting.
        return () => {
            isMounted = false;
        };
    }, [user, setUser, clearUser, navigate]);
};
