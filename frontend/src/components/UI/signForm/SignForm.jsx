import google from './google.svg';
import Input from '../input/Input.jsx';
import Button from "../button/Button";

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
                  <Input type="password" id="password" name="user_password" label="Password"/>
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