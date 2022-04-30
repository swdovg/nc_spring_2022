import React, {useContext, useState, useRef, useEffect} from 'react';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './AddressForm.module.css'

const LOCATION_URL = "api/v1/location";

const AddressForm = ({children, ...props}) => {

    const axiosPrivate = useAxiosPrivate();
    const [location, setLocation] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axiosPrivate.post(
               LOCATION_URL,
               JSON.stringify({value : location}),
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
        }
        setLocation("");
    }

    return (
        <div>
            <h2 className={classes.heading}>Add new address</h2>
            <hr className={classes.line} />
            <form method="post" onSubmit={handleSubmit}  className={classes.form}>
                        <Input type="text" id="address" name="address" label="New Address" value = {location} onChange={(e)=> setLocation(e.target.value)}/>
                <Button>
                    Save changes
                </Button>
            </form>
        </div>
    );
};

export default AddressForm;