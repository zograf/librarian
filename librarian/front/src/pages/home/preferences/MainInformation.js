import { useEffect, useState } from "react"
import { DropDownInput, DropDownSelect } from "../../../components/drop_down/DropDown"
import axios from "axios"
import { API } from "../../../enviroment"

export default function MainInformation({age : userAge, targetYear : year}) {

    const token = localStorage.getItem("token")
    const targetYearOptions = [{ label: "Old Books", value: "OLD" }, { label: "Modern Books", value: "MODERN" }, { label: "New Books", value: "NEW" }]

    const [age, setAge] = useState(undefined)
    const handleAge = (e) => setAge(e.target.value)
    useEffect(() => setAge(userAge), [userAge])

    const [targetYear, setTargetYear] = useState(0)
    const handleTargetYear = (val) => { setTargetYear(val) }
    useEffect(() => { setTargetYear(year) }, [year])

    const [editMode, setEditMode] = useState(false)

    const save = () => {
        if (editMode) {
            let payload = new FormData()
            payload.append('age', age)
            payload.append('targetYear', targetYear)
            axios.put(API + "/user/preferences/main", payload, { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => {
                    setAge(res.data.age)
                    setTargetYear(res.data.targetYear)
                })
                .catch(e => alert(e))
        }
        setEditMode(!editMode)
    }
    
    return(
        <div>
            
            <div className="flex center space-between gap-l">
                <p className="section-title hi-spacer-xs">Main Information</p>
                <button className="text-button v-spacer-s" onClick={save}>{editMode ? 'Save' : 'Edit Main Information'}</button>
            </div>
            
            <div className="flex gap-xs">
                <div className="input-wrapper regular-border v-spacer-xs">
                    <span className="material-symbols-outlined icon input-icon">elderly</span>
                    <input placeholder="Age" type="number" disabled={!editMode} min={5} value={age} onChange={handleAge}/>
                </div>
                <DropDownSelect placeholder={"Prefered Book Age"} icon={"menu_book"} enabled={editMode} options={targetYearOptions} initialValue={targetYear} callback={handleTargetYear}/>
            </div>

        </div>
    )
}