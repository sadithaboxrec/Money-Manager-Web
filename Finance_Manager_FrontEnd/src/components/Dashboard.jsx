import Menubar from "./Menubar.jsx";
import Sidebar from "./Sidebar.jsx";
import {useContext} from "react";
import {AppContext} from "../context/AppContext.jsx";
import { useUser } from "../hooks/useUser.jsx";

const Dashboard = ({children, activeMenu}) => {

    const { user } = useContext(AppContext);

    useUser();
    
    return (
        <div>
            <Menubar activeMenu={activeMenu} />

            {user && (
                <div className="flex">
                    {/* <div className="max-[1080px]:hidden">
                        <Sidebar activeMenu={activeMenu}/>
                    </div> */}

                    <div className="hidden min-[1081px]:block">
                    <Sidebar activeMenu={activeMenu} />
        </div>

                    <div className="grow mx-5">{children}</div>
                </div>
            )}
        </div>
    )
}

export default Dashboard;