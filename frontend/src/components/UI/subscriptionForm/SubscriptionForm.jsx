import React, {setState} from 'react';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import classes from './SubscriptionForm.module.css';
import Select from '../select/Select';

class SubscriptionForm extends React.Component {

    state = {
        count: 0,
    }

    addNewInput = () => {
        this.setState(({ count }) => ({
          count: count + 1,
        }));
        console.log(this.state.count);
    }

    render() {
        return (
        <div>
            <h2 className={classes.heading}>Manage Subscription</h2>
            <hr className={classes.line} />
            <form method="post" className={classes.form}>
                <ul className={classes.form_inputs}>
                    <li>
                        <Input type="text" id="subscription-name" name="subscription-name" label="Name"/>
                    </li>
                    <li>
                        <Input type="number" id="price" name="price" label="Price"/>
                    </li>
                    <li>
                      <Select
                          name="currency" required="required"
                          defaultValue="Currency"
                          options={[
                              {value:"1", name:"USD"},
                              {value:"2", name:"RUB"}
                          ]}
                          label="Currency"
                      />
                    </li>
                    <li>
                      <Input type="text" id="description" name="description" label="Description"/>
                    </li>
                </ul>
                <Button onClick={this.addNewInput}>
                    Add question
                </Button>
                {[...Array(this.state.count)].map(() => <Input />)}
                <Button>
                    Save changes
                </Button>
            </form>
        </div>
        );
    }
};

export default SubscriptionForm;