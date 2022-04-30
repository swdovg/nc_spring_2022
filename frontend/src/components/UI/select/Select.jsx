import React from 'react';
import classes from './Select.module.css';

const Select = ({children,defaultValue, value, label, onChange}) => {
    return (
        <div>
            <label className={classes.label}>{label}</label>
            <select value={value} className={classes.select} onChange = {onChange}>
                <option value="" disabled selected>{defaultValue}</option>
                {children}
            </select>
        </div>
    );
};
export default Select;