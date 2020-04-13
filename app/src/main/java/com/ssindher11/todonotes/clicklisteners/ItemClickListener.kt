package com.ssindher11.todonotes.clicklisteners

import com.ssindher11.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
}