import React from 'react';
import classes from './Input.module.css';

const Input = (props) => {
    return (
        <div>
            <label className={classes.label}>{props.label}
            <input className={classes.input} {...props}/>
            </label>
        </div>
    );
};

export default Input;