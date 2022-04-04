import React from 'react';
import classes from './Textarea.module.css';

const Textarea = ({id, name, label, maxlength}) => {
    return (
        <div>
            <label className={classes.label}>{label}</label>
            <textarea className={classes.textarea} id={id} name={name} maxlength={maxlength}/>
        </div>
    );
};

export default Textarea;