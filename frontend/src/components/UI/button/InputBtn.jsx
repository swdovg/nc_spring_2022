import React, {useState} from 'react';
import classes from './Button.module.css';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import Button from "./Button.jsx";

const InputBtn = ({children, url, ...props}) => {

     const axiosPrivate = useAxiosPrivate();
     const [image, setImage] = useState({});

    const onFileChange = (e) => {
        e.preventDefault();
        console.log(e.target.files);
        setImage(e.target.files[0]);
    };

     const upload = async (e, url) =>  {
        e.preventDefault();

        const formData = new FormData();
        formData.append(
            "newUserPic",
            image,
            image.name
        );
        console.log(formData);
        try {
            const response = await axiosPrivate.post(
               url,
               formData,
               {
                   headers: {"Content-type": "multipart/form-data"},
                   withCredentials: true
               }
            )
        }
        catch(err) {
            if (err?.response)
                console.log(err.message);
        }
    } 

    return (
    <form onSubmit={upload}>
        <label className={classes.btn} > {props.label}
            <input type="file" id="fileInput" {...props} className={classes.input} onChange={onFileChange}>
                {children}
            </input>
        </label>
        <Button> Uppload </Button>
    </form>
    );
};

export default InputBtn;