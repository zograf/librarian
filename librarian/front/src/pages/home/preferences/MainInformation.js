import { useEffect, useState } from "react"
import { DropDownInput, DropDownSelect } from "../../../components/drop-down/DropDown"
import axios from "axios"
import { API } from "../../../enviroment"

export default function MainInformation({age : userAge, targetYear : year}) {

    const token = localStorage.getItem("token")
    const targetYearOptions = [{ label: "Any Age", value: "NOT_IMPORTANT" }, { label: "Old Books", value: "OLD" }, { label: "Modern Books", value: "MODERN" }, { label: "New Books", value: "NEW" }]
    const [notSaved, setNotSaved] = useState(false)

    const [age, setAge] = useState(undefined)
    const handleAge = (e) => {
        setAge(e.target.value)
        setNotSaved(e.target.value != userAge)
    }
    useEffect(() => setAge(userAge), [userAge])

    const [targetYear, setTargetYear] = useState(0)
    const handleTargetYear = (val) => { 
        setTargetYear(val) 
        setNotSaved(val != year)
    }
    useEffect(() => { setTargetYear(year) }, [year])


    const save = () => {
        if (notSaved) {
            let payload = new FormData()
            payload.append('age', age)
            payload.append('targetYear', targetYear)
            axios.put(API + "/user/preferences/main", payload, { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => {
                    setAge(res.data.age)
                    setTargetYear(res.data.targetYear)
                    setNotSaved(false)
                })
                .catch(e => alert(e))
        }
    }
    
    return(
        <div>
            
            <div className="flex center gap-l v-spacer-xs">
                <p className="section-title hi-spacer-xs vi-spacer-xxs">Main Information</p>
                {notSaved && <button className="solid-button small-button showing v-spacer-xs" onClick={save}>Save</button>}
            </div>
            
            <div className="flex gap-xs">
                <div className="input-wrapper regular-border v-spacer-xs">
                    <span className="material-symbols-outlined icon input-icon">elderly</span>
                    <input placeholder="Age" type="number" min={5} value={age} onChange={handleAge}/>
                </div>
                <DropDownSelect placeholder={"Prefered Book Age"} icon={"menu_book"} options={targetYearOptions} initialValue={targetYear} callback={handleTargetYear}/>
            </div>

        </div>
    )
}