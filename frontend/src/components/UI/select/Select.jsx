import React from 'react';
import classes from './Select.module.css';

const Select = ({options, defaultValue, value, label}) => {
    return (
        <div>
            <label className={classes.label}>{label}</label>
            <select value={value} className={classes.select}>
                <option disabled  value="">{defaultValue}</option>
                {options.map(option =>
                    <option key={option.value} value={option.value}>
                        {option.name}
                    </option>
                )}
            </select>
        </div>
    );
};

export default Select;