import React from 'react';
import classes from './Textarea.module.css';

const Textarea = (props) => {
    return (
        <div>
            <label className={classes.label}>{props.label}</label>
            <textarea className={classes.textarea} id={props.id} name={props.name} maxLength={props.maxlength} {...props}/>
        </div>
    );
};

export default Textarea;