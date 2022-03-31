import React from 'react';
import classes from './ProfilePhoto.module.css';
import Button from '../button/Button';
import profile_img from './profile-img.png';

const ProfilePhoto = ({children, ...props}) =>  {
    return (
        <div className={classes.profile_photo}>
            <img src={profile_img} alt="Profile Image" className={classes.profile_photo_img} />
            <Button>
                Change  Photo
            </Button>
        </div>
    );
};

export default ProfilePhoto;