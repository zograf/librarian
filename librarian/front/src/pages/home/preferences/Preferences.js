import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"
import AdditionalSubjects from "./AdditionalSubjects"
import LikedAuthors from "./LikedAuthors"
import MainInformation from "./MainInformation"

export default function Preferences() {    
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")

    const [preferences, setPreferences] = useState(undefined)

    useEffect(() => {
        axios.get(API + path,  { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => { console.log(res.data); setPreferences(res.data) })
            .catch(e => console.log(e))
    }, [])

    return(
        <div className="w-100 standard-padding">
            <MainInformation age={preferences?.age} targetYear={preferences?.targetYear}/>
            <AdditionalSubjects subjects={preferences?.additionalSubjects ?? []} />
            <LikedAuthors authors={preferences?.likedAuthors ?? []} />
        </div>
    )
}