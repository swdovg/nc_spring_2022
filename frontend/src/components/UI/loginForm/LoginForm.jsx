import React from 'react';
import classes from './LoginForm.module.css';
import Button from "../button/Button";
import google from './google.svg';
import Input from '../input/Input.jsx';

const LoginForm = ({children, ...props}) => {
    return (
        <form method="post">
            <ul>
                <li>
                    <Input type="email" id="mail" name="user_mail" label="Email" />
                </li>
                <li>
                    <Input type="password" id="password" name="user_password" label = "Password"/>
                </li>
            </ul>
            <a href="#" className="login-forgot-link">Forgot password?</a>
            <Button>
                Login now
            </Button>
            <Button>
                <img className="icon google-icon" src={google} alt="Google icon"/> Or sign-in with google
            </Button>
        </form>
    );
};

export default LoginForm;