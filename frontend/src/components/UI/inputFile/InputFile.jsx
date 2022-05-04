import React, {useState} from 'react';
import classes from './InputFile.module.css';

const InputFile = (props) => {

    const [image, setImage] = useState();

    const onFileChange = (e) => {
        setImage(e.target.files[0]);
        props.updateImage(image);
        console.log(image);
    }

    return (
        <>
            <label for="file-upload" className={classes.file_upload}>
                Choose photo
            </label>
            <input className={classes.input_file} id="file-upload" type="file" onChange={onFileChange}/>
        </>
    );
};

export default InputFile;