import google from './google.svg';
import Input from '../input/Input.jsx';
import Button from "../button/Button";
import Select from '../select/Select';

const SignForm = ({children, ...props}) => {
    return (
        <form method="post">
            <ul>
                <li>
                    <Input type="email" id="mail" name="user_mail" label="Email"/>
                </li>
                <li>
                    <Input type="login" id="login" name="user_login" label="Login"/>
                </li>
                <li>
                    <Input type="text" id="name" name="user_name" label="Name"/>
                </li>
                <li>
                    <Input type="phone" id="phone" name="user_phone" label="Phone Number"/>
                </li>
                <li>
                  <Input type="password" id="password" name="user_password" label="Password"/>
                </li>
                <li>
                  <Select
                      defaultValue="Role"
                      options={[
                          {value:"1", name:"Supplier"},
                          {value:"2", name:"Consumer"}
                      ]}
                      label="Role"
                  />
                </li>
            </ul>
            <Button>
                Sign In
            </Button>
            <Button>
                <img className="icon google-icon" src={google} alt="Google icon"/> Or sign-in with google
            </Button>
        </form>
    );
};

export default SignForm;