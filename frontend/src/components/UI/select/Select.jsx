import React from 'react';
import classes from './Select.module.css';

const Select = ({children, ...props}) => {
    return (
            <label className={classes.label}>{props.label}
                <select className={classes.select} onChange = {props.onChange}>
                    <option value={props.defaultValue} disabled selected>{props.defaultValue}</option>
                    {children}
                </select>
            </label>
    );
};
export default Select;