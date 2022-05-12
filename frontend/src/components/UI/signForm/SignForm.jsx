import React, {useContext, useState, useRef, useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import Select from '../select/Select';
import axios from "../../../api/axios.js"
import {AuthContext} from '../../../context/AuthContext.js';


const EMAIL_REGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const PWD_REGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
const PHONE_REGEX = /^((\+7|7|8)+([0-9]){10})$/;
const LOGIN_REGEX = /^[a-z0-9_-]{3,16}$/;

const REGISTER_URL = "api/v1/auth/register";

const SignForm = () => {

    const userRef = useRef();
    const errRef = useRef();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [validEmail, setValidEmail] = useState(false);

    const [login, setLogin] = useState("");
    const [validLogin, setValidLogin] = useState(false);

    const [name, setName] = useState("");
    const [isConsumer, setConsumer] = useState(false);

    const [phoneNumber, setPhoneNumber] = useState("");
    const [validPhoneNumber, setValidPhoneNumber] = useState(false);

    const [password, setPassword] = useState("");
    const [validPassword, setValidPassword] = useState(false);

    const [errMsg, setErrMsg] = useState("");

    useEffect(() => {
        const result = EMAIL_REGEX.test(email);
        setValidEmail(result);
    }, [email] );

    useEffect(() => {
        const result = PWD_REGEX.test(password);
        setValidPassword(result);
    }, [password] );

    useEffect(() => {
        const result = PHONE_REGEX.test(phoneNumber);
        setValidPhoneNumber(result);
    }, [phoneNumber] );

    useEffect(() => {
        const result = LOGIN_REGEX.test(login);
        setValidLogin(result);
    }, [login] );

    useEffect(()=> {
        setErrMsg("");
    }, [email, login, name, phoneNumber, password, isConsumer]);


    const handleSubmit = async (e) => {
        e.preventDefault();
         try {
            const response = await axios.post(
               REGISTER_URL,
               JSON.stringify({email, password, phoneNumber, name, isConsumer}),
               {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
               }
            )
             navigate("/login");
             //Cookies.set("token", accessToken);

        }
        catch(err) {
            if (!err?.response)
                setErrMsg("No server response");
            else if (err.response?.status===400)
                setErrMsg("Invalid Data");
            else
                setErrMsg("Registration Failed");
            errRef.current.focus();
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <ul>
                <li>
                    <Input
                        type="email"
                        id="email"
                        label="Email"
                        ref={userRef}
                        onChange={(e)=> setEmail(e.target.value)}
                        required
                        aria-invalid={validEmail ? "false" : "true"}
                    />
                </li>
                <li>
                    <Input
                        type="text"
                        id="name"
                        label="Name"
                        onChange={(e)=> setName(e.target.value)}
                        required
                    />
                </li>
                <li>
                    <Input
                        type="phone"
                        id="phone"
                        label="Phone Number"
                        onChange={(e)=> setPhoneNumber(e.target.value)}
                        required
                        aria-invalid={validPhoneNumber ? "false" : "true"}
                        />
                </li>
                <li>
                  <Input
                    type="password"
                    id="password"
                    label="Password"
                    onChange={(e)=> setPassword(e.target.value)}
                    required
                    aria-invalid={validPassword ? "false" : "true"}
                    />
                </li>
                <li>
                  <Select
                      onChange={(e)=> setConsumer(e.target.value)}
                      options={[
                          {value: false, name:"Supplier"},
                          {value: true, name:"Consumer"}
                      ]}
                      label="Role"
                  >
                    <option value= "false">Supplier </option>
                    <option value= "true">Consumer </option>
                  </Select>
                </li>
            </ul>
            <p ref={errRef}>{errMsg}</p>
            <Button disabled={!validEmail||!validPhoneNumber||!validPassword ? true : false}>
                Sign Up
            </Button>
{/*             <Button> */}
{/*                  <img className="icon google-icon" src={google} alt="Google icon" />  */}
{/*                     Or sign-in with google */}
{/*             </Button> */}
        </form>

    );
};

export default SignForm;