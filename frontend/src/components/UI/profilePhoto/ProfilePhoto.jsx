import React, {useState} from 'react';
import classes from './ProfilePhoto.module.css';
import Button from '../button/Button';
import useAxiosPrivate from '../../../hook/useAxiosPrivate.js';
import profile_img from './profile-img.png';
import Modal from '../modal/Modal.jsx';

const POST_IMG_URL = '/api/v1/image/user';

const ProfilePhoto = ({children, ...props}) => {
    const [modalVisible, setModalVisible] = useState(false);
    const axiosPrivate = useAxiosPrivate();
    const [imageUrl, setImageUrl] = useState(profile_img);
    const [image, setImage] = useState();
    const [errMsg, setErrMsg] = useState("");

    const updateImage = () => {
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getImage = async () => {
            try {
                const userResponse = await axiosPrivate.get("api/v1/user", {
                    signal: controller.signal      //to allow to cancel a request
                });
                setImageUrl(userResponse.data.payload.imageUrl);
            } catch (err) {
                console.log(err);
            }
        }
        getImage();

        return () => {
            controller.abort();
        }
    }


    const onFileChange = (e) => {
        e.preventDefault();
        setImage(e.target.files[0]);
    };

    const onFileUpload = async (e) => {

        e.preventDefault();
        const formData = new FormData();
        formData.append("image", image);
        try {
            await axiosPrivate.post(
                POST_IMG_URL,
                formData,
                {
                    headers: {"Content-type": "multipart/form-data"},
                    withCredentials: true
                }
            )
            setImage({});
            setModalVisible(false);
            updateImage();
        } catch (err) {
            if (err?.response)
                setErrMsg(err.message)
            else if (err.response?.status === 500)
                setErrMsg("Maximum size is 1MB");
        }
    }

    updateImage();

    return (
        <div className={classes.profile_photo}>
            <img src={imageUrl} alt="Profile Image" className={classes.profile_photo_img}/>
            <Button label="Change Photo" url={POST_IMG_URL} onClick={() => setModalVisible(true)}>
                Change Photo
            </Button>

            <Modal visible={modalVisible} setVisible={setModalVisible}>
                <form onSubmit={onFileUpload}>
                    <label className={classes.btn}> {props.label}
                        <input type="file" id="fileInput" {...props} className={classes.input} onChange={onFileChange}>

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