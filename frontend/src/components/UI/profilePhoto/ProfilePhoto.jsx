import React, {useContext, useState, useEffect, useRef} from 'react';
import classes from './ProfilePhoto.module.css';
import Button from '../button/Button';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import profile_img from "./profile-img.png";
import Modal from '../modal/Modal.jsx';

const POST_IMG_URL = "/api/v1/image/user";

const ProfilePhoto = ({children,  ...props}) =>  {
     const [modalVisible, setModalVisible] = useState(false);
     const axiosPrivate = useAxiosPrivate();
     const [image, setImage] = useState({});
     const [errMsg, setErrMsg] = useState("");

     useEffect( () => {
/*         let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getImage = async () => {
            try {
                const response = await axiosPrivate.get("api/v1/image", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setImage(response?.data);

            } catch(err) {
                console.log(err);
                setImage(`${profile_img}`);
            }
        }
        getImage();

        return () =>{
            isMounted=false;
            controller.abort();
        } */
    }, [image]);

     const upload = async (e) =>  {
        e.preventDefault();
        const formData = new FormData();
        formData.append("image", image);
        console.log(formData);
        try {
            const response = await axiosPrivate.post(
               POST_IMG_URL,
               formData,
               {
                   headers: {"Content-type": "multipart/form-data"},
                   withCredentials: true
               }
            )
            setImage({});
            setModalVisible(false);
        }
        catch(err) {
            if (err?.response)
                setErrMsg(err.message)
            else if (err.response?.status === 500)
                setErrMsg("Maximum size is 1MB");
        }

    }

    return (
        <div className={classes.profile_photo}>
            <img src={image} alt="Profile Image" className={classes.profile_photo_img} />
            <Button label="Change Photo" url={POST_IMG_URL} onClick={()=>setModalVisible(true)}>
                Change Photo
            </Button>

            <Modal visible={modalVisible} setVisible={setModalVisible}>
                <form onSubmit={upload}>
                    <label className={classes.btn} > {props.label}
                        <input type="file" id="fileInput" {...props} className={classes.input} onChange={(e)=> setImage(e.target.files[0])}>
                            {children}
                        </input>
                    </label>
                    <Button> Upload </Button>
                    <p> {errMsg}</p>
                </form>
            </Modal>
        </div>
    );
};

export default ProfilePhoto;