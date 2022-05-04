import React, {useContext, useState, useRef, useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import classes from './LoginForm.module.css';
import Button from "../button/Button";
import google from './google.svg';
import Input from '../input/Input.jsx';
import {Link} from "react-router-dom";
import axios from "../../../api/axios.js"
import useAuth from '../../../hook/useAuth.js';
import Cookies from 'js-cookie';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"

const LOGIN_URL = "api/v1/auth/login";

const LoginForm = () => {
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const {auth, setAuth} = useAuth();

    const userRef = useRef();
    const errRef = useRef();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errMsg, setErrMsg] = useState("");

    useEffect(()=> {
        setErrMsg("");
    }, [email, password]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(
                LOGIN_URL,
                JSON.stringify({email, password}),
                {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
                }
            )
            const accessToken = response.data?.payload.token;
            const role = response.data?.payload.role;

            setAuth({email, password, role, accessToken});
            Cookies.set("token", accessToken);

            let isMounted = true;
            const controller = new AbortController(); //to cansel request if the component on mounting

            const getUserInfo = async () => {
                try {
                    const response = await axiosPrivate.get("api/v1/user", {
                        signal: controller.signal      //to allow to cansel a request
                    });
                    Cookies.set("user", JSON.stringify(response.data?.payload))
                } catch(err) {
                    console.log(err);
                }
            }
            getUserInfo();
            navigate("/");
            return () =>{
                isMounted=false;
                controller.abort();
            }
        }
        catch(err) {
            if (!err?.response)
                setErrMsg("No server response");
            else if (err.response?.status===400)
                setErrMsg("Wrong Email or Password");
            else
                setErrMsg("Login Failed");
            errRef.current.focus();
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <ul>
                <li>
                    <Input
                        type="email"
                        id="mail"
                        label="Email"
                        ref={userRef}
                        onChange={(e)=> setEmail(e.target.value)}
                        required/>
                </li>
                <li>
                    <Input
                        type="password"
                        id="password"
                        label = "Password"
                        onChange={(e)=> setPassword(e.target.value)}
                        required/>
                </li>
            </ul>
            <p ref={errRef}>{errMsg}</p>
            {/* <Link to="#" className="login-forgot-link">Forgot password?</Link> */}
            <Button>
                Login now
            </Button>
{/*             <Button>
                <img className="icon google-icon" src={google} alt="Google icon"/> Or sign-in with google
            </Button> */}
        </form>
    );
};

export default LoginForm;