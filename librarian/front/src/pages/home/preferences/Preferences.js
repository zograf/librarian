import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import AdditionalSubjects from "./AdditionalSubjects"

// { headers: {"Authorization" : `Bearer ${token}`} }

export default function Preferences() {    
    let path = '/user/preferences/'
    const userId = localStorage.getItem("id")
    const token = localStorage.getItem("token")
    const email = localStorage.getItem("username")

    const [age, setAge] = useState(undefined)
    const [likedKeywords, setLikedKeywords] = useState([])
    const [searchSent, setSearchSent] = useState(false)

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                setAge(res.data.age)
                setLikedKeywords(res.data.additionalSubjects)
                console.log(res.data)
            })
            .catch(e => console.log(e))
    }, [])

    const handleAge = (e) => setAge(e.target.value)


    return(
        <div className="w-100 standard-padding">
        <p className="section-title hi-spacer-xs">Main Information</p>

            <div className="flex">
                <div className="input-wrapper regular-border v-spacer-xs">
                    <span className="material-symbols-outlined icon input-icon">elderly</span>
                    <input placeholder="Age" type="number" min={5} value={age} onChange={handleAge}/>
                </div>
            </div>

            <AdditionalSubjects additionalSubjects={likedKeywords} />

        </div>
    )
}