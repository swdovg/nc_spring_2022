import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './AddressForm.module.css'

const AddressForm = ({children, ...props}) => {
    return (
        <div>
            <h2 className={classes.heading}>Add new address</h2>
            <hr className={classes.line} />
            <form method="post" className={classes.form}>
                <ul className={classes.form_inputs}>
                    <li>
                        <Input type="text" id="country" name="address_country" label="Country"/>
                    </li>
                    <li>
                        <Input type="text" id="city" name="address_city" label="City"/>
                    </li>
                    <li>
                      <Input type="text" id="street" name="address_street" label="Street"/>
                    </li>
                    <li>
                      <Input type="number" id="house" name="address_house" label="House"/>
                    </li>
                    <li>
                      <Input type="number" id="flat" name="address_flat" label="Flat"/>
                    </li>
                </ul>
                <Button>
                    Save changes
                </Button>
            </form>
        </div>
    );
};

export default AddressForm;