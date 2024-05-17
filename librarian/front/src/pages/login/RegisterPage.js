import React, { useState, useMemo } from "react";
import { DropDownInput, DropDownSelect } from "../../components/drop_down/DropDown";
import axios from "axios";
import { API } from "../../enviroment";

export function RegisterPage() {
    return(
        <main className="fh-100 w-100 flex center justify-center">
            <RegisterForm/>
        </main>
    )
}

function RegisterForm() {

    const [genders, _] = useState([{ label: "Male", value: 0 }, { label: "Female", value: 1 }])

    const [name, setName] = useState("")
    const [age, setAge] = useState(undefined)
    const [gender, setGender] = useState(-1)
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [repeatPassword, setRepeatPassword] = useState("")

    const handleName = (e) => setName(e.target.value)
    const handleAge = (e) => setAge(e.target.value)
    const handleGender = (val) => setGender(val)
    const handleEmail = (e) => setEmail(e.target.value);
    const handlePassword = (e) => setPassword(e.target.value);
    const handleRepeatPassword = (e) => setRepeatPassword(e.target.value);
    
    const handleSubmit = (e) => {
        e.preventDefault()
        let payload = {
            "name" : name,
            "email" : email,
            "age" : Number(age),
            "gender" : gender,
            "password" : password
        }
        axios.post(API + "/user/register", payload)
            .then(res => { window.location.href = "/" })
            .catch(e => alert("Opsie - Registration failed"))
    }

    return(
        <div style={{width: 'clamp(200px, 25%, 280px)'}}>
            <p className="flex center justify-center page-title">Register</p>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">person</span>
                <input placeholder="Name" value={name} onChange={handleName}/>
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">elderly</span>
                <input placeholder="Age" type="number" min={5} value={age} onChange={handleAge}/>
            </div>
            <DropDownSelect placeholder={"Gender"} icon={"wc"} options={genders} callback={handleGender} enabled={true}/>
            <div className="input-wrapper regular-border v-spacer-xs vi-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">mail</span>
                <input placeholder="Email" type="email" value={email} onChange={handleEmail} />
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">lock_open</span>
                <input placeholder="Password" type="Password" value={password} onChange={handlePassword}/>
            </div>
            <div className="input-wrapper regular-border v-spacer-xs">
                <span className="material-symbols-outlined icon input-icon">lock</span>
                <input placeholder="Repeat Password" type="Password" value={repeatPassword} onChange={handleRepeatPassword}/>
            </div>
            <button className='solid-accent-button w-100 v-spacer-xs' onClick={handleSubmit}>Create Account</button>
            <button className='outline-button w-100' onClick={() => { window.location.href = "/"}}>Back to Login</button>
        </div>
    )

}