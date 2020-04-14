package com.ssindher11.todonotes.clicklisteners

import com.ssindher11.todonotes.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}