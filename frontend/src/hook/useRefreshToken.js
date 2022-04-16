import axios from "../api/axios.js";
import useAuth from "./useAuth.js";

const useRefreshToken = () => {
    const {auth, setAuth} = useAuth();

    const refresh = async () => {
        const token= auth.accessToken;
        console.log(token);
        const response = await axios.get('api/v1/auth/refreshToken', {
            withCredentials: true,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            }
        });
        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(response.data.message);
            return { ...prev, accessToken: response.data.message }
        });
        return response.data.message;
    }
    return refresh;
};

export default useRefreshToken;
