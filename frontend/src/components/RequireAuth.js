import {useLocation, Navigate, Outlet} from "react-router-dom";
import useAuth from "../hook/useAuth.js";

const RequireAuth = ({allowedRole}) => {

    const {auth} = useAuth();
    const location = useLocation();

    return (
        allowedRole.includes(auth.role)
            ? <Outlet />
            : <Navigate to="/" state={{from: location}} replace />
    );
}

export default RequireAuth;