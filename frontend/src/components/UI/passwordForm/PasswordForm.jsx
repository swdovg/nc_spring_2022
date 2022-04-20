import React, {useContext, useState, useRef, useEffect} from 'react';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './PasswordForm.module.css';
import Select from '../select/Select';
import Textarea from '../textarea/Textarea';

const PWD_REGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
const REGISTER_URL = "api/v1/user/password";

const PasswordForm = () => {

    const axiosPrivate = useAxiosPrivate();
    const [errMsg, setErrMsg] = useState("");
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [validPassword, setValidPassword] = useState(false);
    const errRef = useRef();

    useEffect(() => {
        const result = PWD_REGEX.test(newPassword);
        setValidPassword(result);
    }, [newPassword] );

    useEffect(()=> {
        setErrMsg("");
    }, [oldPassword, newPassword]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axiosPrivate.put(
               REGISTER_URL,
               JSON.stringify({oldPassword, newPassword}),
               {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
               }
            )
             console.log(response.data);
        }
        catch(err) {
            if (!err?.response)
                setErrMsg("No server response");
            else if (err.response?.status===400)
                setErrMsg("Invalid Data");
            else
                setErrMsg("Something Failed");
            errRef.current.focus();
        }
    }

    return (

        <div>
            <h2 className={classes.heading}>Change Password</h2>
            <hr className={classes.line} />
            <form onSubmit={handleSubmit} className={classes.form}>
                <ul className={classes.form_inputs}>
                    <li>
                        <Input type="password" id="oldPwd"  label="Old Password" onChange={(e)=> setOldPassword(e.target.value)}/>
                    </li>
                    <li>
                        <Input type="password" id="newPwd" label="New Password" onChange={(e)=> setNewPassword(e.target.value)}/>
                    </li>
                </ul>
                <p ref={errRef}>{errMsg}</p>
                <Button>
                    Save changes
                </Button>
            </form>
        </div>
    );
};

export default PasswordForm;