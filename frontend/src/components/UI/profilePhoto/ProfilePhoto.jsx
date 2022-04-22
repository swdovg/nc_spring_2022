import React, {useContext, useState, useEffect, useRef} from 'react';
import classes from './ProfilePhoto.module.css';
import Button from '../button/Button';
import InputBtn from '../button/InputBtn';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import profile_img from "./profile-img.png";

const POST_IMG_URL = "/api/v1/image/user";

const ProfilePhoto = ({children, ...props}) =>  {
     const axiosPrivate = useAxiosPrivate();
    const [image, setImage] = useState(`${profile_img}`);

/*       useEffect( () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getImage = async () => {
            try {
                const response = await axiosPrivate.get("api/v1/image", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setImage(response.data);

            } catch(err) {
                console.log(err);
                setImage(`${profile_img}`);
            }
        }
        getImage();

        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, []); */

     const fileSelectedHandler = async (e) => {
        console.log(e.target.files[0]);
         setImage(e.target.files[0]);

        const response = await axiosPrivate.post(
           POST_IMG_URL,
           JSON.stringify(image),
           {
               headers: {'Content-Type': 'application/json'},
               withCredentials: true
           }
        )
         console.log(response.data);
    }

    return (
        <div className={classes.profile_photo}>
            <img src={image} alt="Profile Image" className={classes.profile_photo_img} />
            <InputBtn label="Change Photo"/>
        </div>
    );
};

export default ProfilePhoto;