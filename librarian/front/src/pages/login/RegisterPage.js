import React, { useState, useMemo } from "react";
import { DropDownInput } from "../../components/drop_down/DropDown";

export function RegisterPage() {
    return(
        <main className="fh-100 w-100 flex center justify-center">
            <RegisterForm/>
        </main>
    )
}

function RegisterForm() {

    const [genders, _] = useState([{ label: "Male", value: "M" }, { label: "Female", value: "F" }])
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [repeatPassword, setRepeatPassword] = useState("")

    const handleEmail = (e) => setEmail(e.target.value);
    const handlePassword = (e) => setPassword(e.target.value);
    const handleRepeatPassword = (e) => setRepeatPassword(e.target.value);
    const handleGender = (e) => {

    }
    
    const handleSubmit = (e) => {
        e.preventDefault()
    }

    return(
        <div style={{width: 'clamp(200px, 25%, 280px)'}}>
            <p className="flex center justify-center page-title">Register</p>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">person</span>
                <input placeholder="Name" />
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">elderly</span>
                <input placeholder="Age" type="number" />
            </div>
            <DropDownInput placeholder={"Gender*"} icon={"wc"} options={genders} callback={handleGender}/>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">mail</span>
                <input placeholder="Email" type="email" value={email} onChange={handleEmail} />
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">key</span>
                <input placeholder="Password" type="Password" value={password} onChange={handlePassword}/>
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">key</span>
                <input placeholder="Repeat Password" type="Password" value={repeatPassword} onChange={handleRepeatPassword}/>
            </div>
            <button className='solid-accent-button w-100 v-spacer-xs' onClick={handleSubmit}>Create Account</button>
            <button className='outline-button w-100' onClick={() => { window.location.href = "/"}}>Back to Login</button>
        </div>
    )

}