import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import AdditionalSubjects from "./AdditionalSubjects"
import LikedAuthors from "./LikedAuthors"

// { headers: {"Authorization" : `Bearer ${token}`} }

export default function Preferences() {    
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")

    const [age, setAge] = useState(undefined)
    const [likedKeywords, setLikedKeywords] = useState([])
    const [likedAuthors, setLikedAuthors] = useState([])

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                setAge(res.data.age)
                setLikedKeywords(res.data.additionalSubjects)
                setLikedAuthors(res.data.likedAuthors)
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

            <AdditionalSubjects subjects={likedKeywords} />
            <LikedAuthors authors={likedAuthors}/>
        </div>
    )
}