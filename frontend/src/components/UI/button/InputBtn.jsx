import React from 'react';
import classes from './Button.module.css';

const InputBtn = ({children, ...props}) => {
    return (
        <label className={classes.btn} > {props.label}
            <input type="file" {...props} className={classes.input}/>
        </label>
    );
};

export default InputBtn;