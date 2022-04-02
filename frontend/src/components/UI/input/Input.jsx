import React from 'react';
import classes from './Input.module.css';

const Input = ({id, name, type, label}) => {
    return (
        <div>
            <label className={classes.label}>{label}</label>
            <input className={classes.input} type={type} id={id} name={name} />
        </div>
    );
};

export default Input;